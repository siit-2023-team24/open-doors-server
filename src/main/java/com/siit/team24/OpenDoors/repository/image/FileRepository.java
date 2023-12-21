package com.siit.team24.OpenDoors.repository.image;

import com.siit.team24.OpenDoors.dto.image.ImageBytesDTO;
import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.enums.ImageType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;


import java.io.*;
import java.util.Optional;

public class FileRepository {

    //TODO move to aplication.properties
//    private final String path = "Z:\\opendoors";
    private final String path = "C:\\opendoors";

    public Image save(ImageFileDTO fileDto) throws IOException {
        String filepath = getFilepath(fileDto.getImageType(), fileDto.getEntityId());
        createMissingDirectory(filepath);

        File file = new File(filepath + "\\" + fileDto.getFile().getOriginalFilename());
        fileDto.getFile().transferTo(file);
        System.out.println("Received file: " + fileDto.getFile().getOriginalFilename());
        return new Image(fileDto.getImageId(), filepath, fileDto.getFile().getOriginalFilename(), fileDto.getFile().getContentType());
    }

    public Image saveBytes(ImageBytesDTO dto) {
        String filepath = getFilepath(dto.getType(), dto.getEntityId());
        createMissingDirectory(filepath);

        File file = new File(filepath + "\\" + dto.getName());
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(dto.getBytes());
            return new Image(null, filepath, dto.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getFilepath(ImageType type, Long entityId) {
        String folder;
        if (type == ImageType.PROFILE)
            folder = "\\profile\\";
        else if (type == ImageType.ACCOMMODATION)
            folder = "\\accommodation\\";
        else
            folder = "\\pending-accommodation\\";

        return path + folder + entityId;
    }

    private void createMissingDirectory(String filepath) {
        File directory = new File(filepath);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success)
                System.err.println("Error making directory: " + filepath);
            else System.out.println("Success making directory: " + directory.getAbsolutePath());
        }
    }

    public byte[] getFile(Optional<Image> image, boolean isProfile) throws IOException {
        String filepath;
        if (image.isEmpty()) {
            if (isProfile)
                filepath = "./src/main/resources/static/account.png";
            else
                //TODO change path to the new image
                filepath = "./src/main/resources/static/logo.png";
        } else {
            filepath = String.join(File.separator, image.get().getPath(), image.get().getName());
        }
        System.out.println(filepath);
        File file = new File(filepath);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = in.readAllBytes();
        in.close();
        return bytes;
    }

    public MultipartFile getMultipartFile(Optional<Image> image) throws IOException {
        if (image.isEmpty())
            throw new EntityNotFoundException();

        String filepath = String.join(File.separator, image.get().getPath(), image.get().getName());
        File file = new File(filepath);
        return new MockMultipartFile(file.getName(), new FileInputStream(file));
    }

    public void delete(Image image) {
        String filepath = String.join(File.separator, image.getPath(), image.getName());
        File file = new File(filepath);
        System.out.println("Attempting to delete file: " + filepath);

        if (file.delete())
            System.out.println("Deleted image with id: " + image);
        else
            System.err.println("Error deleting image: " + image + " from file system");

        //delete folder if empty
        File folder = new File(image.getPath());
        if (folder.exists() && folder.isDirectory() && folder.listFiles() != null
                && folder.listFiles().length == 0) {
            if (folder.delete())
                System.out.println("Deleted folder: " + image.getPath());
            else
                System.err.println("Error deleting folder: " + image.getPath());
        }
    }
}

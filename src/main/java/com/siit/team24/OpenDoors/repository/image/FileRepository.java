package com.siit.team24.OpenDoors.repository.image;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

public class FileRepository {

    //TODO move to aplication.properties
    private final String path = "Z:\\opendoors";

    public Image save(ImageFileDTO fileDto) throws IOException {
        String folder = fileDto.isProfile()? "\\profile\\" : "\\accommodation\\";
        String filepath = path + folder + fileDto.getEntityId();

        //create dir if needed
        File directory = new File(filepath);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success)
                System.err.println("Error making directory: " + filepath);
            else System.out.println("Success making directory: " + directory.getAbsolutePath());
        }

        File file = new File(filepath + "\\" + fileDto.getFile().getOriginalFilename());
        fileDto.getFile().transferTo(file);
        System.out.println("Received file: " + fileDto.getFile().getOriginalFilename());
        return new Image(fileDto.getImageId(), filepath, fileDto.getFile().getOriginalFilename(), fileDto.getFile().getContentType());
    }

    public byte[] getFile(Optional<Image> image) throws IOException {
        String filepath;
        if (image.isEmpty()) {
            filepath = ".\\src\\main\\resources\\static\\account.png";
        } else {
            filepath = String.join(File.separator, image.get().getPath(), image.get().getName());
        }
        File file = new File(filepath);
        return new BufferedInputStream(new FileInputStream(file)).readAllBytes();
    }

    public void delete(Image image) {
        String filepath = String.join(File.separator, image.getPath(), image.getName());
        File file = new File(filepath);
        if (file.delete())
            System.out.println("Deleted image with id: " + image);
        else
            System.err.println("Error deleting image: " + image + "from file system");
    }
}

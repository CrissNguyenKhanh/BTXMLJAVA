package BaiTapXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class bai1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập đường dẫn của thư mục:");
        String path = scanner.nextLine();

        File directory = new File(path);

        if (!directory.exists()) {
            System.out.println("Thư mục không tồn tại.");
            return;
        }

        if (!directory.isDirectory()) {
            System.out.println("Đường dẫn không phải là một thư mục.");
            return;
        }

        try {
            // Tạo đường dẫn đầy đủ cho file XML trong cùng thư mục với thư mục gốc
            String xmlFileName = directory.getName() + ".xml"; // Sử dụng tên thư mục để đặt tên cho file XML
            FileWriter writer = new FileWriter(new File(directory, xmlFileName));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<directory_structure>\n");
            // Ghi thẻ <directory> với tên của thư mục gốc
            writer.write("<directory>" + directory.getName() + "</directory>\n");
            listDirectory(directory, writer, 1);
            writer.write("</directory_structure>");
            writer.close();
            System.out.println("Đã tạo file XML thành công: " + xmlFileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file XML.");
            e.printStackTrace();
        }

        scanner.close();
    }

    public static void listDirectory(File directory, FileWriter writer, int depth) throws IOException {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                writeDirectoryTag(writer, file, depth);
                listDirectory(file, writer, depth + 1);
                writer.write("</" + file.getName() + ">\n");
            } else {
                writeFileTag(writer, file, depth);
            }
        }
    }

    public static void writeDirectoryTag(FileWriter writer, File directory, int depth) throws IOException {
        String indent = getIndent(depth);
        writer.write(indent + "<" + directory.getName() + ">\n");
    }

    public static void writeFileTag(FileWriter writer, File file, int depth) throws IOException {
        String indent = getIndent(depth);
        writer.write(indent + "<file>" + file.getName() + "</file>\n");
    }

    public static String getIndent(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  "); // Sử dụng hai dấu cách cho mỗi cấp độ
        }
        return indent.toString();
    }
}

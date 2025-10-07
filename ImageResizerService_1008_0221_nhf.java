// 代码生成时间: 2025-10-08 02:21:37
import io.micronaut.core.annotation.Introspected;
    import javax.inject.Singleton;
    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.io.*;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;

    /**
     * Image Resizer Service using Java and Micronaut framework.
     * This service is responsible for resizing images in a batch process.
     */
    @Singleton
    @Introspected
    public class ImageResizerService {

        private static final String OUTPUT_DIR = "./output/"; // Output directory for resized images

        /**
         * Resizes a batch of images with a specified dimension.
         *
         * @param inputDir  Directory containing the original images.
         * @param outputDir Directory to store the resized images.
         * @param targetWidth Target width for resizing.
         * @param targetHeight Target height for resizing.
         * @throws IOException if an I/O error occurs.
         */
        public void resizeImages(String inputDir, String outputDir, int targetWidth, int targetHeight) throws IOException {
            File dir = new File(inputDir);
            if (!dir.exists() || !dir.isDirectory()) {
                throw new IllegalArgumentException("Input directory does not exist or is not a directory.");
            }

            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
            if (files == null) {
                throw new IOException("Failed to list files in the input directory.");
            }

            // Create output directory if it does not exist
            Files.createDirectories(Paths.get(outputDir));

            for (File file : files) {
                resizeImage(file, Paths.get(outputDir, file.getName()), targetWidth, targetHeight);
            }
        }

        /**
         * Resizes a single image and saves it to the specified output path.
         *
         * @param originalFile The original image file.
         * @param outputFile   The output file path for the resized image.
         * @param targetWidth  Target width for resizing.
         * @param targetHeight Target height for resizing.
         * @throws IOException if an I/O error occurs.
         */
        private void resizeImage(File originalFile, Path outputFile, int targetWidth, int targetHeight) throws IOException {
            try (BufferedImage originalImage = ImageIO.read(originalFile)) {
                if (originalImage == null) {
                    throw new IOException("Failed to read image file: " + originalFile.getAbsolutePath());
                }

                double aspectRatio = (double) originalImage.getWidth() / (double) originalImage.getHeight();
                int newWidth, newHeight;

                if (targetWidth > 0 && targetHeight > 0) {
                    newWidth = targetWidth;
                    newHeight = (int) (targetHeight * aspectRatio);
                } else if (targetWidth > 0) {
                    newWidth = targetWidth;
                    newHeight = (int) (originalImage.getHeight() * (targetWidth / (double) originalImage.getWidth()));
                } else {
                    newHeight = targetHeight;
                    newWidth = (int) (originalImage.getWidth() * (targetHeight / (double) originalImage.getHeight()));
                }

                BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                g2d.dispose();

                ImageIO.write(resizedImage, originalFile.getName().substring(originalFile.getName().lastIndexOf('.') + 1), outputFile.toFile());
            }
        }
    }
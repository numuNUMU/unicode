import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class main {
    public static void zip(String rezipFile, String rezipFileName)
    {

    }
    public static void unzip(String zipPath, String zipFileName, String unzipPath)
    {
        File zipFile = new File(zipPath+zipFileName);
        FileInputStream  fis = null;
        ZipInputStream zis =null;
        ZipEntry zipentry= null;
        File file_nfc= null;
        File file_nfd=null;
        FileOutputStream fos_nfc = null;
        FileOutputStream fos_nfd =null;

        try {
            fis=  new FileInputStream(zipFile);
            zis = new ZipInputStream(fis);

            while ((zipentry = zis.getNextEntry()) != null)
            {
                String unNormalFileName = zipentry.getName().replace(".txt.zip","");
                System.out.println("[NFD NAME] "+unNormalFileName);
                String NormalFileName = normalizeNfc(unNormalFileName);
                System.out.println("[NFC NAME] "+ NormalFileName);
                file_nfd = new File(unzipPath, unNormalFileName+".txt");
                file_nfc = new File(unzipPath, NormalFileName +".txt");
                fos_nfc =new FileOutputStream(file_nfc);
                fos_nfd =new FileOutputStream(file_nfd);

                byte[] buffer =new byte[256];
                int size =0;
                while ((size = zis.read(buffer))>0)
                {
                    fos_nfc.write(buffer, 0,  size);
                    fos_nfd.write(buffer, 0,  size);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos_nfc != null)
            {
                try {
                    fos_nfc.close();
                }
                catch (IOException ignored) { }
            }
            if (fos_nfd != null)
            {
                try {
                    fos_nfd.close();
                }
                catch (IOException ignored) { }
            }
        }
    }
    public static String normalizeNfc(String unNormalFileName) {
        if (!Normalizer.isNormalized(unNormalFileName, Normalizer.Form.NFC)) {
            System.out.println("It is NFD!");
            return Normalizer.normalize(unNormalFileName, Normalizer.Form.NFC);
        }
        System.out.println("It is NFC!");
        return unNormalFileName;
    }
    public static void main(String[] args) {
        String zipPath = "C:\\Users\\nmnm1\\Downloads\\";
        //String zipFileName = "test.zip";
        String zipFileName = "유니코드테스트.txt.zip";
        String unzipPath =  "C:\\Users\\nmnm1\\Downloads\\";

        unzip(zipPath,zipFileName,unzipPath);
    }
}

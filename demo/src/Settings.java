import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static Settings instance;
    private final String repoType;
    private final String repoFileT;
    private final String repoFileC;

    private Settings(String repoType, String repoFileT, String repoFileC) {
        this.repoType = repoType;
        this.repoFileT = repoFileT;
        this.repoFileC = repoFileC;
    }

    public String getRepoFileT() {
        return repoFileT;
    }
    public String getRepoFileC() {
        return repoFileC;
    }
    public String getRepoType() {
        return repoType;
    }

    private static Properties loadSettings() {
        try (FileReader fr = new FileReader("C:\\Users\\mario\\Desktop\\MAP\\a4-MarioTavi18\\a4-MarioTavi18\\src\\settings.properties")) {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Settings getInstance() {
        Properties properties = loadSettings();

        instance = new Settings(properties.getProperty("repo_type"), properties.getProperty("repo_fileT"),properties.getProperty("repo_fileC"));
        return instance;
    }


}

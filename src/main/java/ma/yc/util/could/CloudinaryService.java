package ma.yc.util.could;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.enterprise.context.ApplicationScoped;
import ma.yc.util.DotenvReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static ma.yc.util.DotenvReader.get;

@ApplicationScoped
public class CloudinaryService {

    private static final String CLOUD_NAME = get("CLOUD_NAME");
    private static final String API_KEY = get("API_KEY");
    private static final String API_SECRET = get("API_SECRET");

    private Cloudinary cloudinary;

    public CloudinaryService () {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUD_NAME, "api_key", API_KEY, "api_secret", API_SECRET));
    }

    public String uploadFile ( byte[] fileBytes ) throws IOException {
        Map uploadParams = ObjectUtils.asMap("resource_type", "raw", "overwrite", true);

        Map uploadResult = cloudinary.uploader().upload(fileBytes, uploadParams);
        return (String) uploadResult.get("secure_url");
    }

    public String uploadFile ( InputStream inputStream, String publicId ) throws IOException {
        Map uploadParams = ObjectUtils.asMap("resource_type", "raw", "public_id", publicId, "overwrite", true);

        Map uploadResult = cloudinary.uploader().upload(inputStream, uploadParams);
        return (String) uploadResult.get("secure_url");
    }
}

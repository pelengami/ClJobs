package Models;

import java.util.ArrayList;
import java.util.List;

public class FullJobInfo {
    public String Title;
    public String Body;
    public String Compensation;
    public String Type;
    public List<String> Tags = new ArrayList();
    public String Location;
    public String Link;

    public FullJobInfo() {
    }

    public void addTag(String tag) {
        if (!this.Tags.contains(tag)) {
            this.Tags.add(tag);
        }
    }
}

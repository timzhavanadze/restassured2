package Models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersModel {
    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    private String name,
            job;
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("job")
    public void setJob(String job) {
        this.job = job;
    }
}

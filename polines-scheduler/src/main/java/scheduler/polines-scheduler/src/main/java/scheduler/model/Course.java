package scheduler.model;

public class Course {
    private int id;
    private String code;
    private String subject;
    private String prodi;
    private int sks;
    private String type;

    public Course(int id, String code, String subject, String prodi, int sks, String type) {
        this.id = id;
        this.code = code;
        this.subject = subject;
        this.prodi = prodi;
        this.sks = sks;
        this.type = type;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getSubject() { return subject; }
    public String getProdi() { return prodi; }
    public int getSks() { return sks; }
    public String getType() { return type; }

    public void setId(int id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setProdi(String prodi) { this.prodi = prodi; }
    public void setSks(int sks) { this.sks = sks; }
    public void setType(String type) { this.type = type; }
}
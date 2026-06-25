package scheduler.model;

public class Lecturer {
    private int id;
    private String name;
    private int maxSks;
    private String prodi;

    public Lecturer(int id, String name, int maxSks, String prodi) {
        this.id = id;
        this.name = name;
        this.maxSks = maxSks;
        this.prodi = prodi;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getMaxSks() { return maxSks; }
    public String getProdi() { return prodi; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMaxSks(int maxSks) { this.maxSks = maxSks; }
    public void setProdi(String prodi) { this.prodi = prodi; }
}
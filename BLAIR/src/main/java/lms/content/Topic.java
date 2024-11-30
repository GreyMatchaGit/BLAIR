package lms.content;
import lms.Course;

public class Topic extends Content {
    private Course course;
    private Material material;

    public Topic(String title, String description, Course course, Material material) {
        super(title, description);
        this.course = course;
        this.material = material;
    }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public Material getMaterial() { return material; }

    public void setMaterial(Material material) { this.material = material; }

    static class Material {
        private String attachment;
        private String description;

        public Material(String attachment, String description) {
            this.attachment = attachment;
            this.description = description;
        }

        public String getAttachment() { return attachment; }
        public String getDescription() { return description; }

        public void setDescription(String description) { this.description = description; }
        public void setAttachment(String attachment) { this.attachment = attachment; }
    }
}

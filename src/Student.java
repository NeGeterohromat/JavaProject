public class Student {
    private final int ulearnID;

    public Student(String ulearnID) {
        this.ulearnID = hashCode(ulearnID);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            return ((Student) obj).ulearnID == ulearnID;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ulearnID;
    }

    private int hashCode(String s) {
        return s.hashCode();
    }
}

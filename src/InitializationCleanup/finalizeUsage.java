package InitializationCleanup;

public class finalizeUsage {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Collect it");
        super.finalize();
    }

    public static void main(String[] args) {
        finalizeUsage a = new finalizeUsage();
        System.out.println("afer gc");
    }
}

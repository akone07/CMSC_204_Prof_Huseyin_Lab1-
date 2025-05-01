package lab4;

public interface ChatClientInterface extends Runnable {

    /**
     * Prompt for and return the desired screen name.
     */
    public String getName();

    public int getServerPort();
}

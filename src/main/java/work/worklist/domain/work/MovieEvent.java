package work.worklist.domain.work;

public class MovieEvent {

    private int id;
    public String title;
    public String content;

    public MovieEvent(int id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId(){
        return id;
    }
}

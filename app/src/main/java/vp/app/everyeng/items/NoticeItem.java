package vp.app.everyeng.items;

import java.io.Serializable;

/**
 * Created by Android on 2017. 12. 20..
 */

public class NoticeItem implements Serializable{

    private String title;
    private String message;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

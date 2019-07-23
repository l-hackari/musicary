package musicary.model;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String email;
    private Date bornDate;

    public User(){
        username = "";
        password = "";
    }

    public User(String username, String password) {

        this.username = username;
        this.password = password;

    }

    public User(String username, String password, String email) {

        this.username = username;
        this.password = password;
        this.email = email;

    }


    public void setId(int id)                      { this.id = id;            }
    public void setUsername(String username)       { this.username = username;}
    public void setPassword(String password)       { this.password = password;}
    public void setEmail(String email)             { this.email = email;      }
    public void setBornDate(Date bornDate)       { this.bornDate = bornDate;}



    public int getId()          { return id;        }
    public String getUsername() { return username;  }
    public String getPassword() { return password;  }
    public String getEmail()    { return email;     }
    public Date getBornDate() { return bornDate;  }


}

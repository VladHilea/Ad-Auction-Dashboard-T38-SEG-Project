package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServersInterval extends Interval {
    private ArrayList<Server> servers;

    public ServersInterval(LocalDateTime date, ArrayList<Server> servers) {
        super(date);

        this.servers = servers;
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
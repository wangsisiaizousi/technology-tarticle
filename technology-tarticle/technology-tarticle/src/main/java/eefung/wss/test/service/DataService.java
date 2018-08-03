package eefung.wss.test.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import eefung.wss.test.entity.Tnew;
import eefung.wss.test.units.CSDNJsoupUtils;

@Service
public class DataService {

    public List<Tnew> getAllData() throws IOException {
        InputStream is = DataService.class.getClassLoader().getResourceAsStream("data/test.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        List<Tnew> ss = new ArrayList<Tnew>();
        String tmpline = "";
        String line = bufferedReader.readLine();
        Tnew tnew = new Tnew();
        while (tmpline != null) {
            line = bufferedReader.readLine();
            tnew.setDate(line);
            line = bufferedReader.readLine();
            tnew.setTitle(line);
            line = bufferedReader.readLine();
            tmpline = line;
            while (tmpline != null && !tmpline.equals("===")) {
                line += "<p>" + tmpline + "</p>";
                tmpline = bufferedReader.readLine();
            }
            tnew.setContent(line);
            ss.add(tnew);
            tnew = new Tnew();
        }
        return ss;
    }

    public List<Tnew> getCSDNTnews() throws Exception {
        return CSDNJsoupUtils.getCSDNTnews();
    }
}

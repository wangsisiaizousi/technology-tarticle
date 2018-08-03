package eefung.wss.test.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eefung.wss.test.entity.Tnew;
import eefung.wss.test.service.DataService;
import freemarker.template.Template;

@Controller
public class DataController {
    @Autowired
    private DataService dataService;

    @RequestMapping("/getData")
    @ResponseBody
    public List<Tnew> getData() {
        List<Tnew> ss = null;
        try {
            ss = dataService.getAllData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ss;
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/contentPage")
    public ModelAndView contentPage(@RequestParam String num) {
        ModelAndView mav = new ModelAndView("contentPage");
        mav.addObject("num", num);
        return mav;
    }

    @RequestMapping("/getDataPustFile")
    public void getDataPustFile() {
        List<Tnew> tnews = null;
        File file = null;
        try {
            file = new File(Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"data/test.txt");
            if(file.exists()) {
                file.delete();
                file.createNewFile();
            }else {
                file.createNewFile();
            }
            tnews = dataService.getCSDNTnews();
            for (Tnew tnew : tnews) {
                
                String articleAll ="===\n"+tnew.getDate()+"\n"+tnew.getTitle()+"\n"+tnew.getContent()+"\n";
                insert(file,0,articleAll);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insert(File file,int pos,String insertContent) throws IOException{//pos是插入的位置
        File tmp = File.createTempFile("tmp",null);
        tmp.deleteOnExit();
        RandomAccessFile raf=null;
        FileOutputStream tmpOut=null;
        FileInputStream tmpIn = null;
        try{
            raf = new RandomAccessFile(file,"rw");
            tmpOut = new FileOutputStream(tmp);
            tmpIn = new FileInputStream(tmp);
            raf.seek(pos);//首先的话是0
            byte[] buf = new byte[64];
            int hasRead = 0;
            while((hasRead = raf.read(buf))>0){
                //把原有内容读入临时文件
                tmpOut.write(buf,0,hasRead);            
            }
            raf.seek(pos);
            raf.write(insertContent.getBytes());
            //追加临时文件的内容
            while((hasRead = tmpIn.read(buf))>0){
                raf.write(buf,0,hasRead);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            raf.close();
            tmpIn.close();
            tmpOut.close();
        }
     
    }
    public static void main(String[] args) {
        System.out.println(DataController.class.getResource("/").toString()+"data/test.txt");
    }

}

package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JsonView implements View{

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper=new ObjectMapper();
        out.print(mapper.writeValueAsString(createModel(request)));
    }

    public Map<String, Object> createModel(HttpServletRequest request){

        Enumeration<String> names=request.getAttributeNames();
        Map<String, Object> model=new HashMap<>();

        while(names.hasMoreElements()){
            String name=names.nextElement();
            model.put(name, request.getAttribute(name));
        }

        return model;
    }
}

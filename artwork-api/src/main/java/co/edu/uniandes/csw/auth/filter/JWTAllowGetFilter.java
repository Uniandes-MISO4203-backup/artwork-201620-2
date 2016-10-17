/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.util.WebUtils;

/**
 *
 * @author juan
 */
public class JWTAllowGetFilter extends JWTFilter  {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        
        String httpMethod = WebUtils.toHttp(request).getMethod();
        
        if ("GET".equals(httpMethod)) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
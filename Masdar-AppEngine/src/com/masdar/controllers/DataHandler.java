/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masdar.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.masdar.entities.Idea;
import com.masdar.entities.IdeaEndpoint;
import com.masdar.entities.SocialIdeaUser;
import com.masdar.entities.SocialIdeaUserEndpoint;

/**
 *
 * @author Amr_Abdelkader
 */
public class DataHandler extends HttpServlet {

   
	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	String getUsersString(List<Long> usersIds){
		StringBuilder stringBuilder = new StringBuilder();
		for (Long userId : usersIds) {
			stringBuilder.append(userId+",");
		}
		return stringBuilder.substring(0, stringBuilder.toString().length() - 1 );
	}
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        try {
//        	IdeaEndpoint endpoint = new IdeaEndpoint();
//        	Idea newIdea = new Idea();
//        	newIdea.setIdea_blob_key("123456789");
//        	newIdea.setIdea_header("Testing Area v2");
//        	newIdea.setIdea_type(2);
//        	newIdea.setUser_id(Long.valueOf(12));
//        	newIdea.setVoting_number(Long.valueOf(0));
//        	//endpoint.insertSocialIdea(newIdea, "22222,33333,44444");
//        	List<Long> list = new ArrayList<Long>();
//        	list.add(Long.valueOf(2));
//        	list.add(Long.valueOf(4));
//        	endpoint.insertSocialIdea(newIdea, getUsersString(list));
        	
        	SocialIdeaUserEndpoint socialEndpoint = new SocialIdeaUserEndpoint();
        	
//        	SocialIdeaUser testupdate = new SocialIdeaUser();
//        	testupdate.setIsLocked(1);
//        	testupdate.setIdea_id(Long.valueOf(31));
//        	testupdate.setUser_id(Long.valueOf(2));
//        	socialEndpoint.insertSocialIdeaUser(testupdate);
        	
        	SocialIdeaUser siu = socialEndpoint.getIdeaStatus(Long.valueOf(31));
        	if(siu != null){
        		out.print(siu.getIsLocked());
        	}else{
        		out.print("siu == null");
        	}
        	
        	//UserEndpoint end = new UserEndpoint();
        	//out.print(end.getSocialIdeaUsers(Long.valueOf(27)).getItems().size());
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

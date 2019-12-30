package com.bobo.zktest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.curator.framework.recipes.leader.LeaderSelector;

@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter{

	public MyFilter(LeaderSelector leaderSelector){
		this.leaderSelector = leaderSelector;
	}
	private LeaderSelector leaderSelector;
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
		if(leaderSelector.hasLeadership())
			chain.doFilter(request, response);
		else
			response.getWriter().write("is not serving");
	}
}

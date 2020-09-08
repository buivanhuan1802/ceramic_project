package com.individual.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.individual.Entity.AppUser;
import com.individual.Entity.UserRole;
import com.individual.ServiceImpl.AppRoleServiceImpl;
import com.individual.ServiceImpl.AppUserServiceImpl;
import com.individual.ServiceImpl.UserRoleServiceImpl;
import com.individual.Utils.CommonConstant;

@Controller
public class StartUpController {

	@Autowired
	AppUserServiceImpl appUser;
	@Autowired
	AppRoleServiceImpl appRole;


	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String Login(@RequestParam(name = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("loginfail", "1");
		}
		return "login";
	}

	@RequestMapping("/logout")
	public String Logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = { "/403" }, method = RequestMethod.GET)
	public String AccessDenied() {
		return "403";

	}

	@RequestMapping(value = { "/", "/admin", "/user" }, method = RequestMethod.GET)
	public String DefaultUrl(HttpServletRequest req, Principal a) {
		String adminUrl = "redirect:/admin/dashboard";
		String userUrl = "redirect:/user/dashboard";
		HttpSession ss = req.getSession(false);
		if (ss == null) {
			return "redirect:/login";
		}
		AppUser user = appUser.findUserAccount(a.getName());
		List<String> roleName = appRole.getRoleNames(user.getUserId());
		if (roleName.contains(CommonConstant.ROLE_ADMIN)) {
			return adminUrl;
		}
		return userUrl;
	}
}
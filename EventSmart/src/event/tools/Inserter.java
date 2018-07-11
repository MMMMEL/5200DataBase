package event.tools;

import event.dal.*;
import event.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Inserter {
	
	public static void main(String[] args) throws SQLException {
		PersonsDao personsDao = PersonsDao.getInstance();
		AdministratorsDao administratorsDao = AdministratorsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		DIYersDao diyersDao = DIYersDao.getInstance();
		PlannersDao plannersDao = PlannersDao.getInstance();
		
		
		//Create objects.
		Persons person = new Persons ("person", "password", "email");
		person = personsDao.create(person);
		Users user = new Users ("user", "userPassword", "userEmail", "firstname", "lastname", "phone");
		user = usersDao.create(user);
				
		Date lastLogin = new Date();
		Administrators admin1 = new Administrators ("wxy", "password1", "email1", lastLogin);
		admin1 = administratorsDao.create(admin1);
		Administrators admin2 = new Administrators ("gml", "password2", "email2", lastLogin);
		admin2 = administratorsDao.create(admin2);
		Administrators admin3 = new Administrators ("lc", "password3", "email3", lastLogin);
		admin3 = administratorsDao.create(admin3);
		
		DIYers diyer1 = new DIYers ("melissa", "password01", "email01", "melissa", "potter", "9998887777");
		diyer1 = diyersDao.create(diyer1);
		DIYers diyer2 = new DIYers ("molly", "password02", "email02", "molly", "wartson", "6665554444");
		diyer2 = diyersDao.create(diyer2);
		DIYers diyer3 = new DIYers ("chen", "password03", "email03", "chen", "ron", "3332221111");
		diyer3 = diyersDao.create(diyer3);
		
		Planners planner1 = new Planners ("p1", "password001", "email001", "plan", "one", "1112223333", "company1");
		planner1 = plannersDao.create(planner1);
		Planners planner2 = new Planners ("p2", "password002", "email002", "plann", "two", "4445556666", "company1");
		planner2 = plannersDao.create(planner2);
		Planners planner3 = new Planners ("p3", "password003", "email003", "planne", "three", "7778889999", "company2");
		planner3 = plannersDao.create(planner3);
		
		
		
		//READ
		Administrators a1 = administratorsDao.getAdministratorFromUserName("wxy");
		System.out.format("Reading administrator: username:%s email:%s lastLogin:%s \n", 
				a1.getUserName(), a1.getEmail(), a1.getLastLogin());
		DIYers d1 = diyersDao.getDIYerFromDIYerName("molly");
		System.out.format("Reading DIYer: username:%s email:%s firstname:%s, lastname:%s, phone:%s \n", 
				d1.getUserName(), d1.getEmail(), d1.getFirstName(), d1.getLastName(), d1.getPhone());
		Planners p1 = plannersDao.getPlannerFromPlannerName("p1");
		System.out.format("Reading Planner: plannername:%s, firstname:%s, lastname:%s, email:%s, company:%s \n", 
				p1.getUserName(), p1.getFirstName(), p1.getLastName(), p1.getEmail(), p1.getCompany());
		List<Planners> plannerList = plannersDao.getPlannerFromCompanyName("company1");
		for(Planners p : plannerList) {
			System.out.format("Looping planners:  plannername:%s, firstname:%s, lastname:%s, email:%s, company:%s \n", 
					p.getUserName(), p.getFirstName(), p.getLastName(), p.getEmail(), p.getCompany());
		}
		
		
		
		
		//UPDATE
		administratorsDao.updatePassword(admin1, "newPassword");
		System.out.format("Updating admin1 new password: username:%s, password:%s \n", 
				admin1.getUserName(), admin1.getPassWord());
		diyersDao.updateDIYerPassword(diyer1, "newPassword");
		System.out.format("Updating diyer1 new password: username:%s, password:%s \n", 
				diyer1.getUserName(), diyer1.getPassWord());
		plannersDao.updatePlannerPassword(planner1, "newPassword");
		System.out.format("Updating planner1 new password: plannername:%s, password:%s \n", 
				planner1.getUserName(), planner1.getPassWord());
		plannersDao.updatePlannerCompany(planner1, "company3");
		System.out.format("Updating planner1 new company: plannername:%s, company:%s \n", 
				planner1.getUserName(), planner1.getCompany());
		
		
//		//DELETE
//		administratorsDao.delete(admin3);
//		diyersDao.delete(diyer3);
//		plannersDao.delete(planner3);
//		
	}

}

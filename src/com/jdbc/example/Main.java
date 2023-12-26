package com.jdbc.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		// model.selectOne();
		
		Scanner scan = new Scanner (System.in);
		Model model = new Model();
//		
//		System.out.print("부서아이디>");
//		int id = scan.nextInt();
//		
//		System.out.print("부서이름>");
//		String name = scan.next();
//		
//		System.out.print("매니저아이디>");
//		String m_id = scan.next();
//		
//		System.out.print("부서아이디>");
//		String l_id = scan.next();
//		
//		
//		model.insertOne(id, name, m_id, l_id);
		
//		System.out.print("부서명>");
//		String name = scan.next();
//		
//		System.out.print("매니저 아이디>");
//		String m_id = scan.next();
//		
//		System.out.print("부서 아이디>");
//		int id = scan.nextInt();
//		
//		model.updateOne(name, m_id, id);
//		
//		System.out.print("임플로이 아이디>");
//		int id1 = scan.nextInt();
//		
//		model.deleteOne(id1);
		
		ArrayList<EmployeeVO> list = model.selectTwo();
		
		for(EmployeeVO vo : list) {
			System.out.println("-----------------------------");
			System.out.println(vo.getEmployeeId());
			System.out.println(vo.getFirstName());
			System.out.println(vo.getSalary());
			System.out.println(vo.getDepartmentName());
			
		}
	}
}
package com.jdbc.example;

import java.sql.*;
import java.util.ArrayList;

public class Model {
	
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String uid = "hr";
	private String upw = "hr";
	
	//select할 내용작성
	public void selectOne() {
		
		String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID >= ?";
		
		//모든 jdbc코드는 try~catch구문에서 작성이 들어가야 합니다. (throws를 던지고 있기 때문에)
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			//1. JDBC드라이버 준비
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. conn객체생성
			conn = DriverManager.getConnection(url, uid, upw);
			
			//3. conn으로부터 statement객체 생성 - sql상태를 지정하기 위한 객체
			pstmt = conn.prepareStatement(sql);
			//?에 대한 값을 채웁니다. 
			//setString(순서,문자열)
			//setInt(순서,숫자)
			//setDouble(순서,실수)
			pstmt.setString(1,"120");
			
			//4. 실행  
			//executeQuery - select문에 사용합니다.
			//executeUpdate - insert, update, delete문에 사용합니다.
				rs = pstmt.executeQuery();
			
			while(rs.next()) { //다음이 있다면 true, 다음이 없다면 false
				
				//rs.getString(컬럼명) - 문자열반환
				//rs.getInt (컬럼명) - 정수반환
				//rs.getDouble(컬럼명) - 실수형반환
				//rs.getDate(컬럼명) - 날짜형반환
				int emp_id = rs.getInt("EMPLOYEE_ID");
				String first_name = rs.getString("FIRST_NAME");
				String phone_number = rs.getString("PHONE_NUMBER");
				//String hire_date = rs.getString("hire_date");
				Timestamp hire_date = rs.getTimestamp("hire_date");
				int salary = rs.getInt("salary");
				
				System.out.println("-------------------------");
				System.out.println("아이디:" + emp_id);
				System.out.println("이름:" + first_name);
				System.out.println("전화번호:" + phone_number);
				System.out.println("입사일:" + hire_date);
				System.out.println("급여:" + salary);
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//반드시 닫아주자!!
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
				
			}
		}
		
		
	}


	//insert할 내용작성
	public void insertOne(int id, String name, String mId, String lId) {
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		//resultSet은 insert에서 필요가 없습니다.
		
		String sql = "INSERT INTO DEPTS VALUES(?,?,?,?)";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//1. conn생성
			conn = DriverManager.getConnection(url, upw, uid);
			
			//2. pstmt생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, mId);
			pstmt.setString(4, lId);
			
			//3.sql실행
			int result = pstmt.executeUpdate(); //성공시 1 or 실패시 0
			
			if(result == 1) {
				System.out.println("인서트 성공");
			}else {
				System.out.println("인서트 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
		
	}

	//update할 내용작성(실습)
	public void updateOne(String name, String m_id , int id) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE DEPTS SET DEPARTMENT_NAME = ?, MANAGER_ID = ? WHERE DEPARTMENT_ID = ?";
		//Main에서 부서아이디, 부서명, 매니저아이디만 받아서, 해당 부서의 부서명과 매니저아이디를 수정해주세요.
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, upw, uid);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, m_id);
			pstmt.setInt(3, id);
			
			int result =  pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("업데이트 성공");
			}else  {
				System.out.println("업데이트 실패");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
	}

	
	//delete할 내용작성(실습)
	public void deleteOne(int id1) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		//Main employee_id를 받아서 emps테이블에서 해당 아이디를 삭제해주세요.
		
		String sql = "DELETE FROM EMPS WHERE EMPLOYEE_ID = ?";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, upw, uid);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id1);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}	
	}

	//조인을 통한 select(실습)
	public ArrayList<EmployeeVO> selectTwo() {
		
		//값을 담을 ArrayList
		ArrayList<EmployeeVO> list =new ArrayList<>();
		
		
		//사원번호, 이름, 부서명 - 급여순으로 정렬해서 10~20번에 속해있는 데이터 출력 employee_id
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs8 = null;
		
	String sql = "select *\r\n"
				+ "from("
				+ "select a.*, \r\n"
				+ "      rownum as rn\r\n"
				+ "    from(select \r\n"
				+ "         e.employee_id,\r\n"
				+ "         e.first_name, \r\n"
				+ "         e.salary, \r\n"
				+ "         d.department_name \r\n"
				+ "         from employees e\r\n"
				+ "         join departments d on e.department_id = d.department_id \r\n"
				+ "         order by e.salary\r\n"
				+ "    )a\r\n"
				+ ")\r\n"
				+ "WHERE rn >= 10 AND rn <= 20";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, upw, uid);
			
			pstmt = conn.prepareStatement(sql);	
			
			
			rs8 = pstmt.executeQuery();
			
			while(rs8.next()) {
				
				//1행에 대한 처리
				int employee_id = rs8.getInt("EMPLOYEE_ID");
				String first_name = rs8.getString("FIRST_NAME");				
				String department_name = rs8.getString("DEPARTMENT_NAME");
				int salary = rs8.getInt("SALARY");
				
				System.out.println("-------------------------");
				System.out.println("아이디:" + employee_id);
				System.out.println("이름:" + first_name);
				System.out.println("급여:" + salary);
				System.out.println("부서이름:" + department_name);
				
				
				//ORM작업
				EmployeeVO vo = new EmployeeVO(employee_id, first_name, salary, department_name);
				list.add(vo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {		
			}
		}	
		return list;
	}
}
package net.macaronics.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.MybatisService;
import net.macaronics.web.dto.MemberVO;

public class MemberDAO {
 
	private SqlSession sqlSession=MybatisService.getFactory().openSession();
	
	private static Logger logger= LogManager.getLogger(MemberDAO.class);
	
	private MemberDAO(){
		
	}
	private static MemberDAO instance;
	
	public static MemberDAO getInstance(){
		if(instance==null){
			instance=new MemberDAO();
		}
		return instance;
	}
	
	
	//유저아이디 체크
	public int confirm(String userid){
		int result=0;
		//result 값이 0 보다 크면  아이디 중복
		try{	
			result=sqlSession.selectOne("member.confirm", userid);
			logger.info(" confirm result - {} ", result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}
	
	
	//회원 정보 불러오기
	public MemberVO getMember(String id){
		MemberVO memberVO=new MemberVO();
		try{
			memberVO=sqlSession.selectOne("member.getMember", id);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			MybatisService.sessionClose(sqlSession);
		}
		return memberVO;
	}
	
	
	//회원 등록
	public void insertMember(MemberVO memberVO){
		try{
			sqlSession.insert("member.insertMember", memberVO);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
	}
	
	
	
}



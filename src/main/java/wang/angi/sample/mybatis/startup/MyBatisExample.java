package wang.angi.sample.mybatis.startup;

import org.apache.ibatis.session.SqlSession;

import wang.angi.sample.mybatis.startup.domain.City;
import wang.angi.sample.mybatis.startup.mapper.CountryMapper;
import wang.angi.sample.mybatis.startup.mapper.HotelMapper;

public class MyBatisExample {
	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();

			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			System.out.println(countryMapper.selectCountryById(1));

			HotelMapper hotelMapper = sqlSession.getMapper(HotelMapper.class);
			System.out.println(hotelMapper.selectHotelById(1));

			City city = sqlSession.selectOne("selectCityById", 1);
			// 一级缓存开启时，下面的sql不会触发执行
			City city1 = sqlSession.selectOne("selectCityById", 1);
			System.out.println(city);
			System.out.println(sqlSession.selectOne("countCityByCreatedBy","Angi").toString());
			sqlSession.commit();
		} catch (Throwable e) {
			System.out.println(e);
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		try {
			sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();

			// 二级缓存开启时，下面的sql不会触发执行
			City city2 = sqlSession.selectOne("selectCityById", 1);
			System.out.println(city2);
			sqlSession.commit();
		} catch (Throwable e) {
			System.out.println(e);
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}

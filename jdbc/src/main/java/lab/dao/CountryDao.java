package lab.dao;

import lab.model.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public final class CountryDao extends JdbcDaoSupport {
	private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
	private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
	private static final String GET_COUNTRY_BY_NAME_SQL = "select * from country where name = '?'";
	private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = '";
    private static final String UPDATE_COUNTRY_NAME_SQL = "update country set name=:name where code_name=:codeName";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
	private DataSource dataSource = getDataSource();

    public List<Country> getCountryList() {
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(final String name) {
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                dataSource);
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                "name", name + "%");
        return namedParameterJdbcTemplate.query(GET_COUNTRIES_BY_NAME_SQL,
                sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(final String codeName, final String newCountryName) {
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("name", newCountryName)
                        .addValue("codeName", codeName);
        jdbcTemplate.update(UPDATE_COUNTRY_NAME_SQL, sqlParameterSource);
    }

    public Country getCountryByCodeName(final String codeName) {
        final JdbcTemplate jdbcTemplate = getJdbcTemplate();
        assert jdbcTemplate != null;

        final String sql = GET_COUNTRY_BY_CODE_NAME_SQL + codeName + "'";
//		System.out.println(sql);

        return jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).get(0);
    }

    public Country getCountryByName(final String name)
            throws CountryNotFoundException {
        final JdbcTemplate jdbcTemplate = getJdbcTemplate();
        assert jdbcTemplate != null;
        final List<Country> countryList = jdbcTemplate.query(GET_COUNTRY_BY_NAME_SQL, COUNTRY_ROW_MAPPER, name);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException();
        }
        return countryList.get(0);
    }
}

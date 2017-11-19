package lab.dao;

import lab.model.Country;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class CountryDao extends NamedParameterJdbcDaoSupport {
	private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
	private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
	private static final String GET_COUNTRY_BY_NAME_SQL = "select * from country where name = :name";
	private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = '";
    private static final String UPDATE_COUNTRY_NAME_SQL = "update country set name=:name where code_name=:codeName";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

//    Returns null
//    final NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();

    public List<Country> getCountryList() {
        return getNamedParameterJdbcTemplate().query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(final String name) {
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                "name", name + "%");
        return getNamedParameterJdbcTemplate().query(GET_COUNTRIES_BY_NAME_SQL,
                sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(final String codeName, final String newCountryName) {
        final SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("name", newCountryName)
                        .addValue("codeName", codeName);
        getNamedParameterJdbcTemplate().update(UPDATE_COUNTRY_NAME_SQL, sqlParameterSource);
    }

    public Country getCountryByCodeName(final String codeName) {
        final String sql = GET_COUNTRY_BY_CODE_NAME_SQL + codeName + "'";
//		System.out.println(sql);

        return getNamedParameterJdbcTemplate().query(sql, COUNTRY_ROW_MAPPER).get(0);
    }

    public Country getCountryByName(final String name) throws CountryNotFoundException {
        final NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
        assert jdbcTemplate != null;
        final List<Country> countryList = jdbcTemplate.query(GET_COUNTRY_BY_NAME_SQL, new MapSqlParameterSource("name", name), COUNTRY_ROW_MAPPER);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException();
        }
        return countryList.get(0);
    }
}

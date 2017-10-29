package lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Country")
//@Table(name = "COUNTRY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
//	@Column(name="code_name")
	private String codeName;
}

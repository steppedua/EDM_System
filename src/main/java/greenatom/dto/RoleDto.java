package greenatom.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString(of = {"id", "name"})
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {
    private Long id;
    private String name;
}

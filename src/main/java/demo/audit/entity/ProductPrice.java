package demo.audit.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Audited
@Entity
@Builder
@Table(name = "product_price")
public class ProductPrice {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private Long amount;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

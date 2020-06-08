package demo.audit.service;

import demo.audit.entity.Product;
import demo.audit.entity.ProductPrice;
import demo.audit.entity.ProductPriceRepository;
import demo.audit.entity.ProductRepository;
import org.hibernate.envers.Audited;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class ProductRevisionServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRevisionService subject;

    private Long productId;

    @BeforeEach
    void setUp() {
        Product product = new Product(null, "iPhone", true, null);
        productRepository.save(product);
        ProductPrice productPrice = new ProductPrice(null, 100L, product);
        product.setProductPrice(productPrice);
        productRepository.save(product);
        productId = product.getId();
    }

    @Test
    void getGroupRevisions() {
        assertThat(productRepository.findRevisions(productId).getContent(), hasSize(2));

        Product product = productRepository.findById(productId).orElse(null);
        product.setName("iPhone-1");
        productRepository.save(product);
        product.setName("iPhone-2");
        productRepository.save(product);
        product.setName("iPhone-3");
        productRepository.save(product);
        product.setName("iPhone-4");
        productRepository.save(product);
        product.setName("iPhone-5");
        productRepository.save(product);
        product.setName("iPhone-6");
        productRepository.save(product);

        assertThat(productRepository.findRevisions(productId).getContent(), hasSize(8));

        // It fails because proxy because EnversRevisionRepositoryImpl.getEntitiesForRevisions
        // cannot get Revision Number from proxy object
        subject.getGroupRevisions(productId, 0);
    }
}
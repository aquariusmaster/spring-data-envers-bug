package demo.audit.service;

import demo.audit.entity.Product;
import demo.audit.entity.ProductPrice;
import demo.audit.entity.ProductPriceRepository;
import demo.audit.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductRevisionService {
    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;

    @Transactional
    public Page<Revision<Long, Product>> getGroupRevisions(Long productId, int page) {
        Page<Revision<Long, Product>> revisions = productRepository.findRevisions(productId, PageRequest.of(page, 5, RevisionSort.desc()));
        Long priceId = revisions.getContent().get(0).getEntity().getProductPrice().getId();
        Page<Revision<Long, ProductPrice>> priceRevisions = productPriceRepository.findRevisions(priceId, PageRequest.of(page, 5, RevisionSort.desc()));
        //some work to generate dto
        return revisions;
    }

}

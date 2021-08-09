package pl.hk.app.part;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public List<Part> getProductCatalog() {
        return partRepository.findAll();
    }

    public void addPart(Part part) {
        partRepository.save(part);
    }

    public Part findByIdPart(Long id) {
        Optional<Part> part = partRepository.findById(id);
        if (part.isPresent()) {
            return part.get();
        } else {
            throw new RuntimeException();
        }
    }

    public void editPart(Part part) {
        Part part1 = findByIdPart(part.getId());
        part1.setName(part.getName());
        part1.setPrice(part.getPrice());
        part1.setCategory(part.getCategory());
        partRepository.save(part1);
    }

    public List<Part> printListOfProductsInCart() {
        return partRepository.findAllBySelected(true);
    }

    public void addProductToCart(Long id) {
        Part part = findByIdPart(id);
        part.setSelected(true);
        partRepository.save(part);
    }

    public void deleteProductFromCart(Long id) {
        Part part = findByIdPart(id);
        part.setSelected(false);
        partRepository.save(part);
    }

    public void deleteProductsFromCart(List<Part> parts) {
        for (Part part : parts) {
            part.setSelected(false);
            partRepository.save(part);
        }
    }
}

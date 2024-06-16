package com.security.analyzer.v1.testchecklistitem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.security.analyzer.v1.testchecklistitem.TestCheckListItem}.
 */
@Service
@Transactional
public class TestCheckListItemServiceImpl implements TestCheckListItemService {

    private final Logger log = LoggerFactory.getLogger(TestCheckListItemServiceImpl.class);

    private final TestCheckListItemRepository testCheckListItemRepository;

    public TestCheckListItemServiceImpl(TestCheckListItemRepository testCheckListItemRepository) {
        this.testCheckListItemRepository = testCheckListItemRepository;
    }

    @Override
    public TestCheckListItem save(TestCheckListItem testCheckListItem) {
        log.debug("Request to save TestCheckListItem : {}", testCheckListItem);
        return testCheckListItemRepository.save(testCheckListItem);
    }

    @Override
    public TestCheckListItem update(TestCheckListItem testCheckListItem) {
        log.debug("Request to update TestCheckListItem : {}", testCheckListItem);
        return testCheckListItemRepository.save(testCheckListItem);
    }

    @Override
    public Optional<TestCheckListItem> partialUpdate(TestCheckListItem testCheckListItem) {
        log.debug("Request to partially update TestCheckListItem : {}", testCheckListItem);

        return testCheckListItemRepository
            .findById(testCheckListItem.getId())
            .map(existingTestCheckListItem -> {
                if (testCheckListItem.getMarked() != null) {
                    existingTestCheckListItem.setMarked(testCheckListItem.getMarked());
                }

                return existingTestCheckListItem;
            })
            .map(testCheckListItemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestCheckListItem> findAll() {
        log.debug("Request to get all TestCheckListItems");
        return testCheckListItemRepository.findAll();
    }

    public Page<TestCheckListItem> findAllWithEagerRelationships(Pageable pageable) {
        return testCheckListItemRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TestCheckListItem> findOne(Long id) {
        log.debug("Request to get TestCheckListItem : {}", id);
        return testCheckListItemRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestCheckListItem : {}", id);
        testCheckListItemRepository.deleteById(id);
    }
}

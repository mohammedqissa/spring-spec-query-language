package com.msampietro.springspecquerylanguage.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.msampietro.springspecquerylanguage.entity.SearchOperation.OR_PREDICATE_FLAG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
class SearchCriteriaTest {

    @Test
    void testSearchCriteriaOrFlagNullResolvesFalse() {
        SearchCriteria searchCriteria = new SearchCriteria(null, "", SearchOperation.EQUALITY, null);
        assertThat(searchCriteria.isOrPredicate()).isFalse();
    }

    @Test
    void testSearchCriteriaOrFlagInvalidValueResolvesFalse() {
        SearchCriteria searchCriteria = new SearchCriteria("_", "", SearchOperation.EQUALITY, null);
        assertThat(searchCriteria.isOrPredicate()).isFalse();
    }

    @Test
    void testSearchCriteriaOrFlagValidValueResolvesTrue() {
        SearchCriteria searchCriteria = new SearchCriteria(OR_PREDICATE_FLAG, "", SearchOperation.EQUALITY, null);
        assertThat(searchCriteria.isOrPredicate()).isTrue();
    }

    @Test
    void testSearchCriteriaDoesNotBuildNestedSearchCriteriaValueWhenSimpleSearch() {
        SearchCriteria searchCriteria = new SearchCriteria(null, "id", SearchOperation.EQUALITY, "1");
        assertThat(searchCriteria.getValue()).isEqualTo("1");
    }

    @Test
    void testSearchCriteriaBuildNestedSearchCriteriaValueWhenNestedSearch() {
        SearchCriteria searchCriteria = new SearchCriteria(null, "", SearchOperation.EQUALITY, "id=1");
        assertThat(((SearchCriteria) searchCriteria.getValue()).getKey()).isEqualTo("id");
        assertThat(((SearchCriteria) searchCriteria.getValue()).getValue()).isEqualTo("1");
        assertThat(((SearchCriteria) searchCriteria.getValue()).getOperation()).isNull();
        assertThat(((SearchCriteria) searchCriteria.getValue()).isOrPredicate()).isFalse();
    }

}

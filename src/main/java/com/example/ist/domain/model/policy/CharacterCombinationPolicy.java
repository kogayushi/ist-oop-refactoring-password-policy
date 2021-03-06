package com.example.ist.domain.model.policy;

import com.example.ist.domain.model.identity.AuthenticationFactor;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CharacterCombinationPolicy implements Policy {
    private static final String INCLUDING_UPPER_CASE_ALPHABET_AT_LEAST_ONE = "^.*[A-Z].*$";
    private static final String INCLUDING_LOWER_CASE_ALPHABET_AT_LEAST_ONE = "^.*[a-z].*$";
    private static final String INCLUDING_NUMBER_AT_LEAST_ONE = "^.*[0-9].*$";
    private static final String NOT_INCLUDING_ALLOWED_CHARACTER = "^(?!(.*[^a-zA-Z0-9!\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~]+.*)).*$";

    private final Set<Policy> policies;

    public CharacterCombinationPolicy() {
        Set<Policy> policies = new LinkedHashSet<>();
        // Composed Regexパターン(のつもり) refer -> https://martinfowler.com/bliki/ComposedRegex.html
        policies.add(new CharacterPolicy(INCLUDING_UPPER_CASE_ALPHABET_AT_LEAST_ONE));
        policies.add(new CharacterPolicy(INCLUDING_LOWER_CASE_ALPHABET_AT_LEAST_ONE));
        policies.add(new CharacterPolicy(INCLUDING_NUMBER_AT_LEAST_ONE));
        policies.add(new CharacterPolicy(NOT_INCLUDING_ALLOWED_CHARACTER));
        this.policies = Collections.unmodifiableSet(policies);
    }

    @Override
    public boolean isSatisfiedBy(AuthenticationFactor factor) {
        for(Policy policy : policies) {
            if(policy.isSatisfiedBy(factor) == false) {
                return false;
            }
        }
        return true;
    }
}

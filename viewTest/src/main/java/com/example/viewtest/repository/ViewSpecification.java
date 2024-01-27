package com.example.viewtest.repository;

import com.example.viewtest.Common.Sex;
import com.example.viewtest.Entity.View;
import org.springframework.data.jpa.domain.Specification;

public class ViewSpecification {

    public static Specification<View> likePlayerName(String playerName) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("playerName"), "%" + playerName + "%");
    }

    public static Specification<View> likeTeamName(String teamName) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("teamName"), "%" + teamName + "%");
    }

    public static Specification<View> rangeAge(int min,  int max) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.between(root.get("age"), min, max);
    }

    public static Specification<View> equalsSex(Sex sex) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("sex"), String.valueOf(sex));
    }

    public static Specification<View> equalsCity(String city) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("city"), city);
    }
}

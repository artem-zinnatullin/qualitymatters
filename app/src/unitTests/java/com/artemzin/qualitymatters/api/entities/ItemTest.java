package com.artemzin.qualitymatters.api.entities;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ItemTest {

    @Test
    public void equals_shouldWorkCorrectly() {
        Item itemOne = Item.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build();
        Item itemTwo = Item.builder().id("id2").imagePreviewUrl("i2").title("Title2").shortDescription("s2").build();

        assertThat(itemOne).isNotEqualTo(itemTwo);
    }

    @Test
    public void hashcode_shouldWorkCorrectly() {
        Item itemOne = Item.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build();
        Item itemOneCopy = Item.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build();
        Item itemTwo = Item.builder().id("id2").imagePreviewUrl("i2").title("Title2").shortDescription("s2").build();

        assertThat(itemOne.hashCode()).isEqualTo(itemOneCopy.hashCode());
        assertThat(itemOne.hashCode()).isNotEqualTo(itemTwo.hashCode());
    }
}
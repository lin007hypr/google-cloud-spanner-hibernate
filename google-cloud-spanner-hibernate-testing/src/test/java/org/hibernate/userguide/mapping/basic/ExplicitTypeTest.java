/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.userguide.mapping.basic;

import org.hibernate.jpa.test.BaseEntityManagerFunctionalTestCase;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

/**
 * @author Vlad Mihalcea
 */
public class ExplicitTypeTest extends BaseEntityManagerFunctionalTestCase {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
			Product.class
		};
	}

	@Test
	public void test() {
		doInJPA( this::entityManagerFactory, entityManager -> {

		} );
	}

	//tag::basic-type-annotation-example[]
	@Entity(name = "Product_ExplicitTypeTest")
	private class Product {

		@Id
		private Integer id;
		
		private String sku;

		@org.hibernate.annotations.Type( type = "nstring" )
		private String name;

		@org.hibernate.annotations.Type( type = "materialized_nclob" )
		private String description;
	}
	//end::basic-type-annotation-example[]
}

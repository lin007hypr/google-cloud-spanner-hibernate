/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.userguide.mapping.basic;

import org.hibernate.annotations.Target;
import org.hibernate.jpa.test.BaseEntityManagerFunctionalTestCase;
import org.junit.Test;

import javax.persistence.*;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.assertEquals;

/**
 * @author Vlad Mihalcea
 */
public class TargetTest extends BaseEntityManagerFunctionalTestCase {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
			City.class,
		};
	}

	@Test
	public void testLifecycle() {
		//tag::mapping-Target-persist-example[]
		doInJPA( this::entityManagerFactory, entityManager -> {

			City cluj = new City();
			cluj.setId(666);
			cluj.setName( "Cluj" );
			cluj.setCoordinates( new GPS( 46.77120, 23.62360 ) );

			entityManager.persist( cluj );
		} );
		//end::mapping-Target-persist-example[]


		//tag::mapping-Target-fetching-example[]
		doInJPA( this::entityManagerFactory, entityManager -> {

			City cluj = entityManager.find( City.class, 666L );

			assertEquals( 46.77120, cluj.getCoordinates().x(), 0.00001 );
			assertEquals( 23.62360, cluj.getCoordinates().y(), 0.00001 );
		} );
		//end::mapping-Target-fetching-example[]
	}

	//tag::mapping-Target-example[]
	public interface Coordinates {
		double x();
		double y();
	}

	@Embeddable
	public static class GPS implements Coordinates {

		private double latitude;

		private double longitude;

		private GPS() {
		}

		public GPS(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		@Override
		public double x() {
			return latitude;
		}

		@Override
		public double y() {
			return longitude;
		}
	}

	@Entity(name = "City_BasicTargetTest")
	private static class City {

		@Id
		private Long id;

		private String name;

		@Embedded
		@Target( GPS.class )
		private Coordinates coordinates;

		//Getters and setters omitted for brevity

	//end::mapping-Target-example[]
		
		public Long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Coordinates getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(Coordinates coordinates) {
			this.coordinates = coordinates;
		}
	//tag::mapping-Target-example[]
	}
	//end::mapping-Target-example[]
}

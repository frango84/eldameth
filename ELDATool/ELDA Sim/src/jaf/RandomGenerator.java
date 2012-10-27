/*****************************************************************
ELDATool
Copyright (C) 2012 G. Fortino

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation;
version 2.1 of the License.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*****************************************************************/

//file RandomGenerator.java

package jaf;

public class RandomGenerator {
	private final int m = 2147483647;
	private final int a = 16807;
	private final int q = m / a; /* 127773 */
	private final int r = m % a; /* 2836 */
	private int seed;

	public RandomGenerator() {
		seed = 314159;
	};

	public RandomGenerator(int seed) {
		this.seed = seed;
	};

	public double random() {
		int tmpseed = a * (seed % q) - r * (seed / q);
		if (tmpseed > 0)
			seed = tmpseed;
		else
			seed = tmpseed + m;
		return seed * (1.0 / m);
	}

	public double uniform(double lo, double up) {
		return (up - lo) * random() + lo;
	}

	public double exponential(double lamda) throws ArithmeticException {
		return -Math.log(random()) / lamda;
	}
};// RandomGenerator

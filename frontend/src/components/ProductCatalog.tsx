import { ProductCard } from './ProductCard';
import { useProducts } from '../hooks/product';

export function ProductCatalog() {
	const products = useProducts();

	return (
		<div className="h-2/3 pt-10 grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
			{products?.content.map((product) => (
				<div
					key={product.id}
					className="flex justify-center items-center"
				>
					<ProductCard product={product} />
				</div>
			))}
		</div>
	);
}

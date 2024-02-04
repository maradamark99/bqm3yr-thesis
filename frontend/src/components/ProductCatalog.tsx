import { ProductCard } from './ProductCard';
import { Pageable, Product } from '../types';

export function ProductCatalog({
	className,
	products,
}: {
	className?: string;
	products?: Pageable<Product | undefined>;
}) {
	return (
		<div className={className}>
			{products?.content.map((product) => (
				<div
					key={product?.id}
					className="flex justify-center items-center"
				>
					<ProductCard product={product!} />
				</div>
			))}
		</div>
	);
}

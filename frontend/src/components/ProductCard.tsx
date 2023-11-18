import { Rating } from '@mui/material';
import { Product } from '../types/product';
import { ImagePlaceHolder } from './ImagePlaceholder';

export function ProductCard({ product }: { product: Product }) {
	return (
		<div className="bg-white text-black rounded-lg m-2 shadow-lg w-72 h-104 flex-row">
			<div className="h-2/3">
				{product.thumbnail_image_url ? (
					<img
						className="h-full w-full"
						src={product.thumbnail_image_url}
					></img>
				) : (
					<ImagePlaceHolder />
				)}
			</div>
			<div className="h-1/3 py-1 px-6">
				<h1>{product.name}</h1>
				<div className="flex flex-row items-center">
					<Rating
						name="half-rating-read"
						value={product.rating_avg}
						defaultValue={0}
						precision={0.5}
						readOnly
					/>
					<span className="font-bold ml-2 mr-1">
						{product.rating_avg}
					</span>
					<span>({product.rating_count})</span>
				</div>
				<h2 className="py-2">{(+product.unit_price).toFixed(2)}$</h2>
			</div>
		</div>
	);
}

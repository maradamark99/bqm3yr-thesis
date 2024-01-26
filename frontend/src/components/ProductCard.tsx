import { Rating } from '@mui/material';
import { ImagePlaceHolder } from './ImagePlaceholder';
import { Product } from '../types';

export function ProductCard({ product }: { product: Product }) {
	return (
		<div className="bg-white text-black rounded-lg m-2 shadow-lg w-72 h-104 flex-row">
			<div className="h-2/3">
				{product.thumbnailUrl ? (
					<img
						className="h-full w-full"
						src={product.thumbnailUrl}
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
						value={3}
						defaultValue={0}
						precision={0.5}
						readOnly
					/>
					<span className="font-bold mx-2">3</span>
					<span>(12)</span>
				</div>
				<h2 className="py-2">{(+product.currentPrice).toFixed(2)}$</h2>
			</div>
		</div>
	);
}

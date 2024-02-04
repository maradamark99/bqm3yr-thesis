import { Rating } from '@mui/material';
import { ImagePlaceHolder } from './ImagePlaceholder';
import { Product } from '../types';
import { useNavigate } from 'react-router';

export function ProductCard({ product }: { product: Product }) {
	const navigate = useNavigate();
	return (
		<div
			className="flex-row bg-white text-black rounded-lg m-4 shadow-lg w-80 h-104 cursor-pointer"
			onClick={() => navigate('/' + product.id)}
		>
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
			<div className="h-1/3 px-6 mt-2">
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
				<div className="flex flex-row my-2 gap-4">
					<h2 className="py-2">
						{(+product.currentPrice).toFixed(2)}$
					</h2>
					<button className="px-2 py-1 bg-blue-500 text-white rounded">
						Add to cart
					</button>
				</div>
			</div>
		</div>
	);
}

import { useParams } from 'react-router';
import { useProductById } from '../hooks/product';
import { ImagePlaceHolder } from '../components/ImagePlaceholder';
import { Rating } from '@mui/material';
import Markdown from 'react-markdown';

export function ProductView() {
	const { id } = useParams();
	const product = useProductById(id!);
	return (
		<div className="flex m-10">
			<div className="w-2/5 flex justify-center items-center">
				<div className="w-96 h-96">
					{product?.thumbnailUrl ? (
						<img
							className="h-full w-full"
							src={product.thumbnailUrl}
						></img>
					) : (
						<ImagePlaceHolder />
					)}
				</div>
			</div>
			<div className="flex flex-col w-2/5 gap-3">
				<h1 className="text-4xl">{product?.name}</h1>
				<div className="flex flex-row items-center">
					<span className="font-bold mx-2">3</span>
					<Rating
						name="half-rating-read"
						value={3}
						defaultValue={0}
						precision={0.5}
						readOnly
					/>
					<span className="mx-2">12 ratings</span>
				</div>
				<div className="text-2xl ml-4">
					<p>{product?.currentPrice}$</p>
				</div>
				<hr></hr>
				<Markdown>{product?.description}</Markdown>
			</div>
		</div>
	);
}

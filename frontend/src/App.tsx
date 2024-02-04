import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import { ProductView } from './pages/ProductView';

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Home />} />
				<Route path="/:id" element={<ProductView />} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;

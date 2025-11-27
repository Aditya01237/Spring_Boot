import { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import api from '../api/axiosConfig.js';
import ApplicationModal from '../components/ApplicationModal.jsx';

const PlacementDetails = () => {
  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [placement, setPlacement] = useState(location.state?.placement || null);
  const [loading, setLoading] = useState(!placement);
  const [error, setError] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchPlacement = async () => {
      try {
        const res = await api.get(`/placements/${id}`);
        setPlacement(res.data);
      } catch (err) {
        console.error(err);
        setError('Could not load placement details.');
      } finally {
        setLoading(false);
      }
    };

    if (!placement) {
      fetchPlacement();
    }
  }, [id, placement]);

  if (loading) {
    return <div className="flex items-center justify-center min-h-screen">Loading placement...</div>;
  }

  if (error || !placement) {
    return (
      <div className="flex flex-col items-center justify-center min-h-screen text-red-600 space-y-4">
        <p>{error || 'Placement not found.'}</p>
        <button
          onClick={() => navigate('/dashboard')}
          className="px-4 py-2 rounded-lg bg-black text-white text-sm font-medium hover:bg-blue-600 transition-colors"
        >
          Back to Dashboard
        </button>
      </div>
    );
  }

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        <button onClick={() => navigate(-1)} className="mb-6 text-sm text-gray-600 hover:text-blue-600 flex items-center gap-2">
          <span className="inline-block">&larr;</span> Back to opportunities
        </button>

        <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
          <div className="p-8 border-b border-gray-100 bg-gradient-to-r from-blue-50 to-indigo-50">
            <div className="flex items-start justify-between gap-6">
              <div>
                <h1 className="text-3xl font-bold text-gray-900 mb-2">{placement.organisation}</h1>
                <p className="text-lg text-gray-700">{placement.profile}</p>
              </div>
              <div className="text-right">
                <div className="text-xs uppercase tracking-wide text-gray-500 mb-1">Min CGPA</div>
                <div className="text-2xl font-extrabold text-blue-600">{placement.minimumGrade}</div>
                <div className="mt-2 inline-flex items-center px-3 py-1 rounded-full bg-blue-100 text-blue-700 text-xs font-semibold">
                  {placement.intake} Seats Available
                </div>
              </div>
            </div>
          </div>

          <div className="p-8 space-y-8">
            <section>
              <h2 className="text-xl font-semibold text-gray-900 mb-3">Role Overview</h2>
              <p className="text-gray-700 leading-relaxed whitespace-pre-line">
                {placement.description || 'No detailed description has been provided for this role yet.'}
              </p>
            </section>

            <section className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div>
                <h3 className="text-lg font-semibold text-gray-900 mb-2">Ideal Candidate</h3>
                <ul className="list-disc list-inside text-gray-700 space-y-1 text-sm">
                  <li>Strong fundamentals in Data Structures and Algorithms.</li>
                  <li>Solid understanding of Object Oriented Programming.</li>
                  <li>Comfortable working with modern web/backend technologies.</li>
                  <li>Good communication and collaboration skills.</li>
                </ul>
              </div>
              <div>
                <h3 className="text-lg font-semibold text-gray-900 mb-2">Required Skills</h3>
                <ul className="list-disc list-inside text-gray-700 space-y-1 text-sm">
                  <li>Proficiency in at least one programming language (Java, Python, JS, etc.).</li>
                  <li>Experience with databases and SQL.</li>
                  <li>Understanding of version control (Git).</li>
                  <li>Ability to write clean, testable code.</li>
                </ul>
              </div>
            </section>

            <section className="flex items-center justify-between pt-4 border-t border-gray-100">
              <div className="text-sm text-gray-500">Review the details carefully before submitting your application.</div>
              <button onClick={openModal} className="px-6 py-2.5 rounded-lg bg-black text-white text-sm font-medium shadow-sm hover:bg-blue-600 transition-colors">
                Apply for this role
              </button>
            </section>
          </div>
        </div>

        {isModalOpen && <ApplicationModal placement={placement} onClose={closeModal} />}
      </div>
    </div>
  );
};

export default PlacementDetails;


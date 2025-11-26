import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';
import ApplicationModal from '../components/ApplicationModal';

const Dashboard = () => {
  const [placements, setPlacements] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filter, setFilter] = useState('all');
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedPlacement, setSelectedPlacement] = useState(null);
  
  const student = JSON.parse(localStorage.getItem('user') || '{}');

  const fetchData = async () => {
    try {
      if (!localStorage.getItem('token')) {
        navigate('/login');
        return;
      }

      const [jobsRes, appsRes] = await Promise.all([
        api.get('/placements/eligible'),
        api.get('/applications/my-applications')
      ]);

      const appliedIds = new Set(appsRes.data.map(app => app.placement.id));
      const unappliedJobs = jobsRes.data.filter(job => !appliedIds.has(job.id));
      setPlacements(unappliedJobs);
    } catch (err) {
      console.error(err);
      // If unauthorized, force re-login
      if (err.response && err.response.status === 401) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        navigate('/login');
        return;
      }
      setError('Could not load jobs.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const openModal = (job) => {
    setSelectedPlacement(job);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedPlacement(null);
    fetchData();
  };

  const filteredPlacements = placements.filter(job => {
    if (filter === 'all') return true;
    if (filter === 'high') return job.minimumGrade >= 8.0;
    if (filter === 'medium') return job.minimumGrade >= 7.0 && job.minimumGrade < 8.0;
    if (filter === 'low') return job.minimumGrade < 7.0;
    return true;
  });

  if (loading) return <div className="flex items-center justify-center min-h-screen">Loading...</div>;
  if (error) return <div className="flex items-center justify-center min-h-screen text-red-600">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Header Section with gradient accent */}
        <div className="mb-8 p-6 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-xl border border-blue-100">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            Welcome, {student.firstName} {student.lastName}
          </h1>
          <p className="text-gray-600 flex items-center gap-2">
            You are eligible for <span className="font-semibold text-blue-600">{placements.length}</span> roles based on your profile.
          </p>
        </div>


        {/* Jobs Grid */}
        {filteredPlacements.length === 0 ? (
          <div className="text-center py-12 bg-white rounded-lg border border-gray-200">
            <p className="text-gray-500">No jobs found.</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredPlacements.map((job) => (
              <div
                key={job.id}
                className="bg-white rounded-xl border border-gray-200 overflow-hidden hover:shadow-xl hover:border-blue-200 transition-all"
              >
                {/* Card Header with blue accent */}
                <div className="p-6 pb-4 border-b border-gray-100 bg-gradient-to-br from-white to-blue-50">
                  <div className="flex items-start justify-between mb-2">
                    <h3 className="text-lg font-bold text-gray-900 flex-1">
                      {job.organisation}
                    </h3>
                  </div>
                  <p className="text-sm text-gray-600 mb-3">{job.profile}</p>
                  <div className="inline-block px-3 py-1 bg-blue-100 text-blue-700 text-xs font-medium rounded-full">
                    {job.intake} Seats Available
                  </div>
                </div>

                {/* Card Body */}
                <div className="p-6">
                  <p className="text-sm text-gray-600 mb-4 line-clamp-3">
                    {job.description}
                  </p>

                  {/* Card Footer */}
                  <div className="flex items-center justify-between pt-4 border-t border-gray-100">
                    <div className="flex items-center gap-2">
                      <span className="text-xs text-gray-500">Min CGPA</span>
                      <span className="text-sm font-bold text-blue-600">
                        {job.minimumGrade}
                      </span>
                    </div>
                    <button
                      onClick={() => openModal(job)}
                      className="bg-black text-white text-sm font-medium px-5 py-2 rounded-lg hover:bg-blue-600 transition-colors shadow-sm"
                    >
                      Apply
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}

        {/* Application Modal */}
        {isModalOpen && selectedPlacement && (
          <ApplicationModal
            placement={selectedPlacement}
            onClose={closeModal}
          />
        )}
      </div>
    </div>
  );
};

export default Dashboard;